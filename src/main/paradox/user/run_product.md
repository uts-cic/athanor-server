## Running Athanor-Server 

1. Move to the directory where you placed the Athanor-Server product. 
Assuming it is in the home directory:

        cd ~/athanor-server

2. Ensure the JVM has enough memory to run the application.

        export _JAVA_OPTIONS="-Xmx2048M"

3. Run the server using Sbt:

         sbt run

   The following output is expected: 

       [info] Loading global plugins from ~/.sbt/0.13/plugins
       [info] Loading project definition from /home/joseph/athanor-server/project
       SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
       SLF4J: Defaulting to no-operation (NOP) logger implementation
       SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
       [info] Set current project to athanorserver (in build file:~/athanor-server/)
       [info] Running au.edu.utscic.athanorserver.Application 
       [INFO] [10/19/2017 12:31:04.459] [run-main-0] [~] Starting http server at 0.0.0.0:8083

   Connect to the Server 

   Start your browse and type the following: 
       http://localhost:8083/v2/health
   
   This should return json:
```json
{
      "message": "ok"
}
   ```
       
       
               
