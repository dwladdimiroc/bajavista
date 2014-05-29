 #!/bin/bash
cd  ./Mallet\ 2.0.7/

bin/mallet import-file --input ../textDiccionary/Obj-Sub --output ../mallet/Obj-Sub.mallet
bin/mallet import-file --input ../textDiccionary/Pos-Neg --output ../mallet/Pos-Neg.mallet
bin/mallet import-file --input ../textDiccionary/Need-Info --output ../mallet/Need-Info.mallet

bin/mallet train-classifier --input ../mallet/Obj-Sub.mallet --output-classifier ../classifier/Obj-Sub.classifier
bin/mallet train-classifier --input ../mallet/Pos-Neg.mallet --output-classifier ../classifier/Pos-Neg.classifier
bin/mallet train-classifier --input ../mallet/Need-Info.mallet --output-classifier ../classifier/Need-Info.classifier

bin/mallet classify-file --input ../text/tweetTop10 --output ../textAnalyzed/tweetTop10Analyzed-classifierObjSub --classifier ../classifier/Obj-Sub.classifier
bin/mallet classify-file --input ../text/tweetTop10 --output ../textAnalyzed/tweetTop10Analyzed-classifierPosNeg --classifier ../classifier/Pos-Neg.classifier
bin/mallet classify-file --input ../text/tweetTop10 --output ../textAnalyzed/tweetTop10Analyzed-classifierNeedInfo --classifier ../classifier/Need-Info.classifier