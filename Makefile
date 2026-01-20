LATEST_TAG?=`git tag|sort -t. -k 1,1n -k 2,2n -k 3,3n -k 4,4n | tail -1`

SHELL := /bin/bash
SDK := source "$$HOME/.sdkman/bin/sdkman-init.sh" && sdk env &&

help:
	cat Makefile.txt

clean:
	$(SDK) ./gradlew clean

pull:
	git pull
	git submodule update --init --recursive
	git submodule foreach git checkout main
	git submodule foreach git pull

.PHONY: build
build:
	$(SDK) ./gradlew build --warning-mode all

build-fast:
	$(SDK) ./gradlew build -Pcheck=false -x test --warning-mode all

release:
	$(SDK) ./gradlew release --warning-mode all

publish: build
	rm -rf $$HOME/.m2/repository/io/documentnode/epub4j-core
	$(SDK) ./gradlew publishToMavenLocal --warning-mode all

publish-remote: publish
	$(SDK) ./gradlew publishMavenJavaPublicationToMavenRepository --warning-mode all

