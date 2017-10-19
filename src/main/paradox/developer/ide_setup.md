## IDE Set-up

An IDE provides a more convenient and efficient way of doing development.
The Intellij IDEA is preferred because of its Scala support, and relative ease
of use.

1. Check that the Scala and SBT plugins are installed.
   Install them if they are missing.

         File-Settings-Plugins

2. Import the project into Intellij IDEA

         File - New - Project from Existing sources

         Navigate to the build.sbt file in the tap directory where you cloned the project source code.


5. Run the program

        Select the SBT run task from the SBT projects panel.

        The SBT project Panel is available via View - Tools Window

    For more information on IDE project setup refer to the [Intellij IDEA Help pages](https://www.jetbrains.com/help/idea/meet-intellij-idea.html)

    See [Language and Framework Specific Guidelines - Scala - Getting started with SBT section](https://www.jetbrains.com/help/idea/getting-started-with-sbt.html)


6. Set up Intellij IDEA to work with git. 

- You can set-up a connection to the GitHub project using the following menu :

    File - Settings - Github 
    
- Use Tools - Tasks and Context to open tasks 
  
  You should see a list of tasks/issues related to Athanor-server which you can select from.
  
  Intellij IDEA can open a branch for you to work on the issue. 
  
- Use VCS git menu items to commit and push your work. 

  Refer to [Version Control with Intellij IDEA] (https://www.jetbrains.com/help/idea/version-control-with-intellij-idea.html)
       
  The following sections are particularly useful: 
  
  - [Using Git Integration](https://www.jetbrains.com/help/idea/using-git-integration.html)      
  - [Checkout an existing project from a remote host](https://www.jetbrains.com/help/idea/using-git-integration.html#clone-repo)
  - [Sync from a remote repository](https://www.jetbrains.com/help/idea/using-git-integration.html#sync-with-remote-repository)
  - [Publish your changes](https://www.jetbrains.com/help/idea/using-git-integration.html#push)