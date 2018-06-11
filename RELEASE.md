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
    
3. We use maven multi-module for `jbot` with `jbot-parent` being the parent and `jbot` and `jbot-example` the child 
modules. With multi-module, building/releasing the parent will build/release the parent as well as the child modules.
To release:

    Do `$ cd JBot` and then `$ mvn release:clean release:prepare` (you can use this option to see if all okay: `-DdryRun=true`)
    
    Provide answers to the questions below:
    ```
    What is the release version for "JBot Parent"? (me.ramswaroop.jbot:jbot-parent) 4.0.0: : 
    What is SCM release tag or label for "JBot Parent"? (me.ramswaroop.jbot:jbot-parent) jbot-parent-4.0.0: : <use jbot-4.0.0 as tag> 
    What is the new development version for "JBot Parent"? (me.ramswaroop.jbot:jbot-parent) 4.0.1-SNAPSHOT: : 
    ```

4. Finally, run `$ mvn release:perform` to release `jbot-parent` (which also releases `jbot` and `jbot-example`).

5. Goto [Sonatype](https://oss.sonatype.org/index.html#stagingRepositories), check the staging repository and if 
everything looks good, go ahead and close it. Refresh the table and then select the repo and click on "Release".

### FAQ about Maven multi-module

1. What is the structure of the parent pom and the child pom?
    
    The parent pom will have the groupId, artifactId, version and packaging defined as well as it should have the 
    modules tag defining all the child modules.
    
    The child pom will have the parent tag stating the co-ordinates of the parent pom as well as the tags defining its
    own co-ordinates (unlike inheritance in maven).
    
    See below for an example:

    <table>
    <tr>
    <th> Parent pom </th>
    <th> Child pom </th>
    </tr>
    <tr>
    <td>
    
    ```xml
    <project>
        <groupId>me.ramswaroop.jbot</groupId>
        <artifactId>jbot-parent</artifactId>
        <version>4.0.2-SNAPSHOT</version>
        <packaging>pom</packaging>
        
        <modules>
            <module>jbot</module>
            <module>jbot-example</module>
        </modules>
    </project>
    ```
    
    </td>
    <td>
    
    ```xml
    <project>
        <parent>
            <groupId>me.ramswaroop.jbot</groupId>
            <artifactId>jbot-parent</artifactId>
            <version>4.0.2-SNAPSHOT</version>
        </parent>
        
        <artifactId>jbot</artifactId>
        <version>4.0.2-SNAPSHOT</version>
        <packaging>jar</packaging>
    </project>
    ```
    </td>
    </tr>
    </table>
    
2. What is the benefit of multi-module in maven?
    
    There are various advantages of multi-module, the most important being the ability to build/release all modules at
    once. You can build/release all modules by just building/releasing the parent module.
    
3. Can child modules have different versions than the parent?

    I had different versions in parent and child modules but when I released jbot-parent `4.0.1`, the version of
    `jbot-example` also changed to `4.0.1`. So, the answer is no. There may be some way but I am not aware of one.
    
4. What about dependencies?

    This is another advantage of multi-module. You can declare common dependencies across modules in the parent pom 
    only but this isn't mandatory.