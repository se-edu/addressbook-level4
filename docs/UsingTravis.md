# Travis CI

[Travis CI](https://travis-ci.org/) is a _Continuous Integration_ platform for GitHub projects.

Travis CI can run the projects' tests automatically whenever new code is pushed to the repo.
This ensures that existing functionality and features have not been broken by the changes.

The current Travis CI set up performs the following things whenever someone push code to the repo:

  * Runs the `./gradlew clean headless allTests coverage coveralls -i` command
    (see [UsingGradle.md](UsingGradle.md) for more details on what this command means).
  * Automatically retries the build up to 3 times if a task fails.
  * Runs additional [repository-wide checks](#repository-wide-checks).

If you would like to customise your travis build further, you can learn more about Travis
from [Travis CI Documentation](https://docs.travis-ci.com/).

## Setting up Travis CI

1. Fork the repo to your own organization.
2. Go to https://travis-ci.org/ and click `Sign in with GitHub`, then enter your GitHub account details if needed.<br>
![Signing into Travis CI](images/signing_in.png)

3. Head to the [Accounts](https://travis-ci.org/profile) page, and find the switch for the forked repository.
    * If the organization is not shown, click `Review and add` as shown below: <br>
      ![Review and add](images/review_and_add.png)<br>
      This should bring you to a GitHub page that manages the access of third-party applications.
      Depending on whether you are the owner of the repository, you can either grant access
      ![Grant Access](images/grant_access.png)<br>
      or request access<br>
      ![Request Access](images/request_access.png)<br>
      to Travis CI so that it can access your commits and build your code.

    * If repository cannot be found, click `Sync account`
4. Activate the switch.<br>
   ![Activate the switch](images/flick_repository_switch.png)
5. This repo comes with a [`.travis.yml`](../.travis.yml) that tells Travis what to do.
   So there is no need for you to create one yourself.
6. To see the CI in action, push a commit to the master branch!
    * Go to the repository and see the pushed commit. There should be an icon which will link you to the Travis build.<br>
      ![Commit build](images/build_pending.png)

    * As the build is run on a provided remote machine, we can only examine the logs it produces:<br>
      ![Travis build](images/travis_build.png)

7. If the build is successful, you should be able to check the coverage details of the tests
   at [Coveralls](http://coveralls.io/)
8. Update the link to the 'build status' badge at the top of the `README.md` to point to the build status of your
   own repo.

## Repository-wide checks

In addition to running Gradle checks, we also configure Travis CI to run some repository-wide checks.
Unlike the Gradle checks which only cover files used in the build process,
these repository-wide checks cover *all* files in the repository.
They check for repository rules which are hard to enforce on development machines such as
line ending requirements.

These checks are implemented as POSIX shell scripts,
and thus can only be run on POSIX-compliant operating systems such as macOS and Linux.
To run all checks locally on these operating systems,
execute the following in the repository root directory:
```shell
./config/travis/run-checks.sh
```
Any warnings or errors will be printed out to the console.

### Implementing new checks

Checks are implemented as executable `check-*` scripts within the `config/travis/` directory.
The `run-checks.sh` script will automatically pick up and run files named as such.

Check scripts should print out errors in the following format:
```
SEVERITY:FILENAME:LINE: MESSAGE
```
where `SEVERITY` is either `ERROR` or `WARN`,
`FILENAME` is the path to the file relative to the current directory,
`LINE` is the line of the file where the error occurred
and `MESSAGE` is the message explaining the error.

Check scripts must exit with a non-zero exit code if any errors occur.
