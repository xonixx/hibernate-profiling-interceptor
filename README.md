# hibernate-profiling-interceptor

Collects low-level SQL statistics per web-request and outputs as as single INFO log at the end of web request.

Example output

```
INFO	com.cmlteam.hibernate.HibernateProfilingInterceptor	SQLPERF [0.073 s] (/endpoint): T=62, S=62, I=0, U=0, D=0
20   (table1)        
20   (table2)            
11   (table3)
```

Here

+ `[0.073 s]` - total request execution duration
+ `(/endpoint)` - the requested URI
+ `T=62` - total SQL queries count 
+ `S=62` - SQL selects count 
+ `I=0` - SQL inserts count 
+ `U=0` - SQL updates count 
+ `D=0` - SQL deletes count 

## Usage

In pom.xml `<dependencies>`:

```xml
<dependency>
    <groupId>com.github.xonixx</groupId>
    <artifactId>hibernate-profiling-interceptor</artifactId>
    <version>v0.9.2</version>
</dependency>
``` 

In pom.xml `<repositories>`

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
``` 
