# Travis CI

## Background
[Travis CI](https://travis-ci.org/) is a continuous integration platform for GitHub projects.

We are able to setup Travis CI such that it is able to run the projects' tests automatically whenever there is a new update. This is to ensure that existing functionality and features have not been broken with the new commits.

See [Travis CI Documentation](https://docs.travis-ci.com/) for more details.

## Setting up Travis CI

1. Fork the repository to your own organization.
2. Go to https://travis-ci.org/ and click `Sign in with GitHub`, then enter your GitHub account details if needed.
![Signing into Travis CI](../../images/signing_in.png)

3. Head to the travis [Accounts](https://travis-ci.org/profile) page, and find the travis CI switch for the forked repository.
    - If repository cannot be found, click `Sync account`
4. Activate the travis CI switch.  
![Activate the switch](../../images/flick_repository_switch.png)
5. Create the following `travis.yml` file in the main folder
```
language: java
matrix:
  include:
    - jdk: oraclejdk8
script: ./gradlew clean headless allTests coverage coveralls -i
before_install:
          - "export DISPLAY=:99.0"
          - "sh -e /etc/init.d/xvfb start"

addons:
  apt:
    packages:
      - oracle-java8-installer
```

To see the CI in action, push a commit to the master branch!