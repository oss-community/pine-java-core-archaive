git tag -d 1.0.0
git push --delete origin 1.0.0
mvn release:rollback
