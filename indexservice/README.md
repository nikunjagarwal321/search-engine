# Parsing + Indexing Service

_This service will parse the downloaded html content, send child urls for crawling and create and maintain inverted index_ 

- Step 1: consume url and s3 filename from kafka stream
- Step 2: fetch file from s3
- Step 3: parse using jsoup --> get child urls, title, body
- Step 4: insert into rds and push in kafka 
- Step 5: parse body and create inverted index
- Step 6: insert/update words to elastic search from inverted index