 #!/bin/bash
cd  ./Mallet\ 2.0.7/

bin/mallet classify-file --input ../text/tweetTop20 --output ../textAnalyzed/tweetTop20Analyzed-classifierObjSub --classifier ../classifier/Obj-Sub.classifier
bin/mallet classify-file --input ../text/tweetTop20 --output ../textAnalyzed/tweetTop20Analyzed-classifierPosNeg --classifier ../classifier/Pos-Neg.classifier
bin/mallet classify-file --input ../text/tweetTop20 --output ../textAnalyzed/tweetTop20Analyzed-classifierNeedInfo --classifier ../classifier/Need-Info.classifier