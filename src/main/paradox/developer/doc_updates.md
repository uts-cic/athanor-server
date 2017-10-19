## Documentation Updates

Paradox is used to update the project documentation which is maintained under the
src/main/paradox directory. 

If you update the documentation as part of your work item, you would need to 
generate the paradox documentation. 

Run the following command:

    sbt paradox; sbt copyDocs

This will generate html files in the doc directory, and any modified files will be 
part of your pull request. 

