PROJECT_DIR := $(dir $(abspath $(lastword $(MAKEFILE_LIST))))
TARGET_DIR=$(PROJECT_DIR)target

CI_BUILD_NUMBER ?= $(USER)-SNAPSHOT
CI_IVY_CACHE ?= $(HOME)/.ivy2
CI_SBT_CACHE ?= $(HOME)/.sbt
CI_WORKDIR ?= $(shell pwd)

VERSION ?= $(CI_BUILD_NUMBER)

BUILDER_TAG = "meetup/sbt-builder:0.1.3"
BASE_TAG = "meetup/git-big-base:$(VERSION)"
PUBLISH_TAG = "meetup/git-big:$(VERSION)"
LATEST_TAG = "meetup/git-big:latest"

help: ## print out all available commands
	@echo Public targets:
	@grep -E '^[^_][^_][a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "  \033[36m%-20s\033[0m %s\n", $$1, $$2}'
	@echo "Private targets: (use at own risk)"
	@grep -E '^__[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "  \033[35m%-20s\033[0m %s\n", $$1, $$2}'

__package-sbt: ## Internal target used by sbt-builder
	# This step includes the running of my unit tests.
	sbt 'docker:publishLocal'

__package-base: ## Create base image used
	docker build --build-arg CI_BUILD_NUMBER=$(CI_BUILD_NUMBER) -t $(BASE_TAG) base

package: __package-base ## Create container
	# Run a docker container mounting the
	# working directory and caches.
	docker run \
		--rm \
		-v $(CI_WORKDIR):/data \
		-v $(CI_IVY_CACHE):/root/.ivy2 \
		-v $(CI_SBT_CACHE):/root/.sbt \
		-v /var/run/docker.sock:/var/run/docker.sock \
		-e VERSION=$(VERSION) \
		$(BUILDER_TAG) \
		make __package-sbt

publish: package ## Packages and then pushes docker image to registry.
	@docker tag $(PUBLISH_TAG) $(LATEST_TAG)
	@docker push $(PUBLISH_TAG)
	@docker push $(LATEST_TAG)

base-tag: ## Used by sbt to get base image for docker.
	@echo $(BASE_TAG)

# Required for SBT.
version:
	@echo $(VERSION)

# Required for Docker plugin.
publish-tag:
	@echo $(PUBLISH_TAG)