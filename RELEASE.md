# JBot Release Procedure

We release JBot using the [maven-release plugin](http://central.sonatype.org/pages/apache-maven.html) method. Before 
releasing, we have to setup gpg/gpg2 on our machine. This is required to sign our artifacts before releasing it to the
Central Repository. We have to generate a key and distribute the public key to a key server. Follow the detailed 
[PGP Signature Guide](http://central.sonatype.org/pages/working-with-pgp-signatures.html) to learn more.

Follow the below steps to release JBot to OSSRH:

1. Make sure you have the `ossrh` profile in `~/.m2/settings.xml`:
    ```xml
    <profile>
        <id>ossrh</id>
        <activation>
           <activeByDefault>true</activeByDefault>
        </activation>
        <properties>
           <gpg.executable>gpg2</gpg.executable>
           <gpg.passphrase>paste passphrase here</gpg.passphrase>
        </properties>
    </profile>
    ```

2. Have the `ossrh` server defined in `~/.m2/settings.xml`:
    ```xml
    <servers>
        <server>
            <id>ossrh</id>
            <username>nexus jira username</username>
            <password>nexus jira password</password>
        </server>
    </servers>
    ```
    
3. Currently, the release of `jbot` is tied to its parent pom so we have to release `jbot-parent`.

    Do `$ cd JBot` and then `$ mvn release:clean release:prepare`
    
    Provide answers to the questions below:
    ```
    What is the release version for "JBot Parent"? (me.ramswaroop.jbot:jbot-parent) 4.0.0: : 
    What is SCM release tag or label for "JBot Parent"? (me.ramswaroop.jbot:jbot-parent) jbot-parent-4.0.0: : <use jbot-4.0.0 as tag> 
    What is the new development version for "JBot Parent"? (me.ramswaroop.jbot:jbot-parent) 4.0.1-SNAPSHOT: : 
    ```

4. Finally, run `$ mvn release:perform` to release `jbot-parent` (which also releases `jbot`).

5. Goto [Sonatype](https://oss.sonatype.org/index.html#stagingRepositories), check the staging repository and if 
everything looks good, go ahead and close it. Refresh the table and then select the repo and click on "Release".

_Future Enhancements: Make the release process of jbot and jbot-parent separate._

    
