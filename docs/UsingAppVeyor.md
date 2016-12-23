# AppVeyor

[AppVeyor](https://www.appveyor.com/) is a _Continuous Integration_ platform for GitHub projects.
It runs its builds on Windows virtual machines.

AppVeyor can run the project's tests automatically whenever new code is pushed to the repo.
This ensures that existing functionality and features have not been broken on Windows by the changes.

The current AppVeyor setup performs the following things whenever someone pushes code to the repo:

* Runs the `gradlew.bat headless allTests` command.

* Automatically retries the build up to 3 times if a task fails.

If you would like to customize your AppVeyor build further, you can learn more about AppVeyor from the
[AppVeyor Documentation](https://www.appveyor.com/docs/).

## Setting up AppVeyor

1. Fork the repo to your own organization.

2. Go to https://ci.appveyor.com/, and under `Login`, click on `GitHub` to login with your GitHub account.
   Enter your GitHub account details if needed.

    ![Click on GitHub in the login page](images/appveyor/login.png)

3. After logging in, you will be brought to your projects dashboard. Click on `NEW PROJECT`.

    ![Click on "NEW PROJECT" in the projects dashboard](images/appveyor/add-project-1.png)

4. You will be brought to the `Select repository` page. Select `GitHub`.

    * On your first usage of AppVeyor, you will need to give AppVeyor authorization to your GitHub account.
      Click on `Authorize GitHub`.

        ![Click on Authorize GitHub](images/appveyor/add-project-2.png)

    * This will bring you to a GitHub page that manages the access of third-party applications to your repositories.

        Depending on whether you are the owner of the repository, you can either grant access:

        ![Grant Access](images/grant_access.png)

        Or request access:

        ![Request Access](images/request_access.png)

5. AppVeyor will then list the repositories you have access to in your GitHub account.
   Find the repository you want to set AppVeyor up on, and then click `ADD`.

    ![Click "Add" on the repository you want to set AppVeyor up on](images/appveyor/add-project-3.png)

6. AppVeyor will then be activated on that repository.
   To see the CI in action, push a commit to any branch!

    * Go to the repository and see the pushed commit. There should be an icon which will link you to the AppVeyor build:

        ![Commit build](images/appveyor/ci-pending.png)

    * As the build is run on a remote machine, we can only examine the logs it produces:

        ![AppVeyor build](images/appveyor/ci-log.png)

7. Update the link to the "build status" badge at the top of `README.md` to point to the AppVeyor build status of your own repo.

    * To find your build status badge URL,
      first go to your project settings by clicking on the "Settings" icon:

        ![Click on project settings](images/appveyor/project-settings-1.png)

    * Then go to the `Badges` section of your project settings by clicking on it:

        ![Click on "Badges"](images/appveyor/project-settings-2.png)

    * Then copy and paste the markdown code for your `master` branch to your `README.md` file:

        ![Copy and paste the markdown code for your `master` branch to your `README.md` file](images/appveyor/project-settings-3.png)
