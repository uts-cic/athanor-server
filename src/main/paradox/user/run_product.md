## Running Athanor-Server 

### Running locally

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
### Running in a Docker environment

1. Use the following command following a local build to
install athanor-server.

       sbt docker:publishLocal

2. Check that the athanor-server image has been installed.


       docker images

   You should see something similar to the following line.

       REPOSITORY          TAG      etc
       athanorserver       0.8

3. Run the docker image :

       docker run "athanorserver:0.8"


   You should see the following log output

       [INFO] [main] [~] Checking grammar path
       etc
       INFO  athanor-server  - docker grammar path =/opt/docker/grammar/
       etc
       [INFO] [main] [~] Starting http server at 0.0.0.0:8083

   You can pass environment variables using the -e parameters. For
   example, to set a different grammar path:

        docker run -e ATHANOR_SERVER_DOCKER_GRAMMAR_PATH='/tmp/grammar' "athanorserver:0.8"

   You should now see the following log output:

        INFO  athanor-server  - docker grammar path =/tmp/grammar

4. You must map the ip address to be able connect to Athanor-Server.
   More instructions about this are available on the
   [docker website](https://docs.docker.com/engine/userguide/networking/default_network/binding/)


