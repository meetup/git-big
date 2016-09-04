PROJECT_DIR := $(dir $(abspath $(lastword $(MAKEFILE_LIST))))
TARGET_DIR=$(PROJECT_DIR)target

CI_BUILD_NUMBER ?= $(USER)-SNAPSHOT
CI_IVY_CACHE ?= $(HOME)/.ivy2
CI_SBT_CACHE ?= $(HOME)/.sbt
CI_WORKDIR ?= $(shell pwd)

VERSION ?= $(CI_BUILD_NUMBER)

BUILDER_TAG = "meetup/sbt-builder:0.1.3"

# lists all available targets
list:
	@sh -c "$(MAKE) -p no_op__ | \
		awk -F':' '/^[a-zA-Z0-9][^\$$#\/\\t=]*:([^=]|$$)/ {split(\$$1,A,/ /);\
		for(i in A)print A[i]}' | \
		grep -v '__\$$' | \
		grep -v 'make\[1\]' | \
		grep -v 'Makefile' | \
		sort"

# required for list
no_op__:

package-sbt:
	sbt test:test publishLocal

# We clean the locally cached version
# of the jar when we're done publishing.
publish-sbt: package-sbt component-test-sbt
	sbt publish cleanLocal

component-test-sbt:
	cd src/component/sbt && sbt component:test

publish:
    docker pull $(BUILDER_TAG)
	docker run \
		--rm \
		-v $(CI_WORKDIR):/data \
		-v $(CI_IVY_CACHE):/root/.ivy2 \
		-v $(CI_SBT_CACHE):/root/.sbt \
		-e VERSION=$(VERSION) \
		$(BUILDER_TAG) \
		make publish-sbt

# Required for SBT.
version:
	@echo $(VERSION)
