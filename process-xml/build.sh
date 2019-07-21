id=$(docker create mwdump)
docker cp $id:/app/mwdumper.jar ./
docker rm -v $id