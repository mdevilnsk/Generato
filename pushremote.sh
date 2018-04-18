#!/bin/bash
ssh-keyscan -t rsa github.com >> ~/.ssh/known_hosts
GITLAB = "$(git remote -v | grep github)"
echo ${GITLAB}
if [${GITLAB} -eq ""]
then
	git remote add github git@github.com:mdevilnsk/Generato 
fi
git push github HEAD:master
