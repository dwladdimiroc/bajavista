 #!/bin/bash
cd  ./Mallet\ 2.0.7/

bin/mallet import-file --input ../textDiccionary/Obj-Sub --output ../mallet/Obj-Sub.mallet --stoplist-file ../stopwords/esp
bin/mallet import-file --input ../textDiccionary/Pos-Neg --output ../mallet/Pos-Neg.mallet --stoplist-file ../stopwords/esp

bin/mallet train-classifier --input ../mallet/Obj-Sub.mallet --output-classifier ../classifier/Obj-Sub.classifier
bin/mallet train-classifier --input ../mallet/Pos-Neg.mallet --output-classifier ../classifier/Pos-Neg.classifier

bin/mallet classify-file --input ../text/tweet --output ../textAnalyzed/tweetAnalyzed-classifierObjSub --classifier ../classifier/Obj-Sub.classifier
bin/mallet classify-file --input ../text/tweet --output ../textAnalyzed/tweetAnalyzed-classifierPosNeg --classifier ../classifier/Pos-Neg.classifier