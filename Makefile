LATEST_TAG?=`git tag|sort -t. -k 1,1n -k 2,2n -k 3,3n -k 4,4n | tail -1`

help:
	cat Makefile.txt

clean:
	./gradlew clean

pull:
	git pull
	git submodule update --init --recursive
	git submodule foreach git checkout master
	git submodule foreach git pull

.PHONY: build
build:
	./gradlew build

build-fast:
	./gradlew build -Pcheck=false -x test

publish:
	rm -rf $$HOME/.m2/repository/io/documentnode/epub4j-core
	./gradlew build publishToMavenLocal

publish-remote:
	./gradlew build publishMavenJavaPublicationToMavenRepository

