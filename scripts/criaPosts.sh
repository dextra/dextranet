
#/bin/bash
for (( n=0;n<$1;n++));do
  a=`expr $n % 3` 
    texto="X Normal"

    if [ $a -eq 0 ] ; then   
      texto="X Divisivel3"
    fi
    sleep 1
	wget --post-data "title=exemplo$n&content=conteudoExemplo$texto" http://localhost:8080/s/post
done; 
rm post*

