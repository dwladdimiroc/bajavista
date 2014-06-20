 #!/bin/bash
cd  ./Mallet\ 2.0.7/

bin/mallet classify-file --input ../text/tweet --output ../textAnalyzed/tweetAnalyzed-classifierObjSub --classifier ../classifier/Obj-Sub.classifier
bin/mallet classify-file --input ../text/tweet --output ../textAnalyzed/tweetAnalyzed-classifierPosNeg --classifier ../classifier/Pos-Neg.classifier
