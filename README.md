# hibernate-profiling-interceptor

[![](https://jitpack.io/v/xonixx/hibernate-profiling-interceptor.svg)](https://jitpack.io/#xonixx/hibernate-profiling-interceptor)

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

## Configuration

In application.yml

```yaml
hibernate-profiling-interceptor:
  enabled: true # set to false to disable the hibernate profiling 
  showSql: true # if include full SQL near the table name - use this for local debug, set to false in prod
  singleQueryCntOk: 10 # the upper count of total SQL queries under which the hibernate profiling log won't show, you can set to 1 to show always
```

Don't forget to call the `hibernateProfilingInterceptor.reset()` and `hibernateProfilingInterceptor.report(...)` where necessary. Ex.: 

```java
import com.cmlteam.hibernate.HibernateProfilingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class HibernateProfilingFilter extends GenericFilterBean {

    private final HibernateProfilingInterceptor hibernateProfilingInterceptor;

    @Autowired
    public HibernateProfilingFilter(HibernateProfilingInterceptor hibernateProfilingInterceptor) {
        this.hibernateProfilingInterceptor = hibernateProfilingInterceptor;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();

        hibernateProfilingInterceptor.reset(); // Step 1

        filterChain.doFilter(servletRequest, servletResponse);

        hibernateProfilingInterceptor.report(requestURI, false); // Step 2
    }
}
```
