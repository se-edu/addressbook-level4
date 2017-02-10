# Using GitHub Pages

[GitHub Pages](https://pages.github.com/) is a static site hosting service that allows users to publish
webpages using files in their GitHub repositories.

## Setting Up

GitHub Pages offers two kinds of sites: user or organization sites and project sites.
Since we are using GitHub Pages to showcase the documentation for our project, we will be creating a project site.

 1. Navigate to the repository's `Settings` tab in GitHub. <br>
    <img src="images/github_repo_settings.png" width="600">
 1. Under the GitHub Pages section, click `Choose a theme` in `Theme chooser`. Pick a theme for the
    project site and click `Select theme` when done.
 1. You can now view the site at `https://<organization-name>.github.io/<repo-name>`. <br>
    e.g. https://se-edu.github.io/addressbook-level4

Note that the publishing source has been automatically set to `master branch`.
This means that the GitHub Pages site will be published using source files in the `master` branch.
Jekyll, a static site generator integrated with GitHub Pages, automatically renders Markdown files
(in the `master` branch) to HTML, which are then deployed to the project site by GitHub Pages.

Now that GitHub Pages has been set up, simply edit the documentation files in Markdown and GitHub Pages will
automatically update the project site when the changes are committed to the `master` branch.

## Viewing the Project Site

The project site URL follows the format `https://<organization-name>.github.io/<repo-name>`,
e.g. https://se-edu.github.io/addressbook-level4.
By default, the [README](../README.md) file is displayed on this page.

For the other pages, the structure of the site follows the structure of the repository. <br>
For example, `docs/UserGuide.md` is published at `https://se-edu.github.io/addressbook-level4/docs/UserGuide.html`.

## Customizing the Project Site

For instructions on customizing the project site, refer to [GitHub's guides](https://help.github.com/categories/customizing-github-pages/).

## Troubleshooting

 **Problem: Markdown is not displayed properly in GitHub Pages even though they appear correctly in GitHub preview**

 * Reason: Jekyll (which converts Markdown to HTML for GitHub Pages) uses kramdown as its markdown engine which
   is different from GitHub's own markdown engine. Hence, the same Markdown file is rendered differently in
   GitHub's preview and on GitHub Pages.
 * Solution: kramdown uses stricter syntax than what GitHub Flavored Markdown allows, so use
   [kramdown's syntax](https://kramdown.gettalong.org/parser/gfm.html) if something is not rendered properly
   on the project site.
