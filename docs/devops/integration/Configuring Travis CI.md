# Travis CI

## Background
[Travis CI](https://travis-ci.org/) is a continuous integration platform for GitHub projects.

We are able to setup Travis CI such that it is able to run the projects' tests automatically whenever there is a new update. This is to ensure that existing functionality and features have not been broken with the new commits.

The current Travis CI set up:
  - runs the `./gradlew clean headless allTests coverage coveralls -i` command.
  - automatically retries the build up to 3 times if a task fails

If you would like to customise your travis build, do read the [Travis CI Documentation](https://docs.travis-ci.com/).

## Setting up Travis CI

1. Fork the repository to your own organization.
2. Go to https://travis-ci.org/ and click `Sign in with GitHub`, then enter your GitHub account details if needed.
![Signing into Travis CI](../../images/signing_in.png)

3. Head to the [Accounts](https://travis-ci.org/profile) page, and find the switch for the forked repository.
    - If repository cannot be found, click `Sync account`
4. Activate the switch.  
![Activate the switch](../../images/flick_repository_switch.png)
5. Create the following `.travis.yml` file in the main folder
    ```
    language: java
    matrix:
      include:
        - jdk: oraclejdk8
    script: travis_retry ./gradlew clean headless allTests coverage coveralls -i
    before_install:
              - "export DISPLAY=:99.0"
              - "sh -e /etc/init.d/xvfb start"
    
    addons:
      apt:
        packages:
          - oracle-java8-installer
    ```
6. To see the CI in action, push a commit to the master branch!  
Go to the repository and see the pushed commit. There should be an icon which will link you to the Travis build.
![Commit build](../../images/build_pending.png)

As the build is run on a provided remote machine, we can only examine the logs it produces:
![Travis build](../../images/travis_build.png)

7. If the build is successful, you should be able to check the coverage details of the tests at [Coveralls](http://coveralls.io/)!