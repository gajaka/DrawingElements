SBT_OPTS="-Xms512M -Xmx1024M -Xss1M -XX:+CMSClassUnloadingEnabled"
java $SBT_OPTS -jar sbt-launch.jar "$@"