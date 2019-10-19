# maven-x2p-plugin
Read xml-file and access data as properties for use with maven filtering

Example:
Put meta-desc.xml from test/resources next to pom.xml in your project, and add the plugin:

```
<plugin>
    <groupId>no.codify</groupId>
    <artifactId>maven-x2p-plugin</artifactId>
    <version>1.0-SNAPSHOT</version>
    <executions>
        <execution>
            <configuration>
                <input>meta-desc.xml</input>
            </configuration>
            <goals>
                <goal>xml-project-properties</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```                 
Now you can use maven filtering on your source and have a property-file that gets content from your xml file and other sources (like maven itself)

meta-desc.xml
```
<meta-desc>
    <app>
        <name>foo-bar-app</name>
        <sysdoc.link>http://foo-bar.doc</sysdoc.link>
    </app>
</meta-desc>
```


src/main/resources/application.properties:
```
buildTime=${timestamp}
mvnVersion=${project.version}
app.name=${meta-desc.app.name}
```

pom.xml
```
    <build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
            </resource>
        </resources>
    </build>
```

target/classes/application.properties:
```
buildTime=2019-10-19 18:07
mvnVersion=1.0-SNAPSHOT
app.name=foo-bar-app
```
