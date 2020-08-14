# datatable-pdf-generator
A wrapper utility to generate PDF content based on datatable format and input datalist provided to the same.

It is a java based utility wrapper written around Apache PDFBox utility. 

Purpose of this utility wrapper is to reduce boilerplate code require to generate PDF document for datatable specific needs and its related use-cases, as those has been seen across various industry specific use-cases. 

This wrapper utility will generalize the input and simplifies the overall process of pdf generation programmatically wherever there is a need of generating PDF reports based on data table formats, this will further improve efficiency around wherever there is a need for PDF reports based out of standard data table structure and format.


Data Table PDF Generator Guideline.pdf

This document provides detailed API specification and various functions developed as part of this abstract utility based out of ApahcePDFBox

Sample snippets are also provided for reference implementation.

PDFUtil.jar

This is executable JAR which can be used in any Java based project or it can be instantiated as containerized(e.g. Docker) REST API

response.pdf

Sample output response generated for sample code snippet.

Next Steps

Following are some possible next steps which can further improve and enhance this overall concept:

1. Addressing complexities and more use cases built around PDG report generation.
2. Including other reporting wrappers such spread-sheet, XML, TEXT based or Chart based wrappers.
 