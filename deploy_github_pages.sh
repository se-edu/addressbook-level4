#!/usr/bin/env bash
#
# Pushes asciidoctor generated files to gh-pages branch.

set -e # exit with nonzero exit code if any line fails

GITHUB_REPO="se-edu/addressbook-level4.git"
commit_sha=$(git rev-parse --short HEAD)

cd build/docs/html5

git init
git config user.name "Travis"
git config user.email "travis@travis-ci.org"

git remote add upstream "https://${GITHUB_API_KEY}@github.com/${GITHUB_REPO}"
git fetch upstream
git reset upstream/gh-pages

git add -A .
git commit -m "Rebuild pages at ${commit_sha}"
git push -q upstream HEAD:gh-pages
