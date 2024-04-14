#!/bin/bash
# Replace the Kubernetes API server address
sed -i 's/https:\/\/127.0.0.1:/https:\/\/host.docker.internal:/g' /Users/aindamutsa/.kube/config

# Start the main process (zsh in this case)
exec "$@"


docker run -it --rm --net=host \
--name working-container \
-v /var/run/docker.sock:/var/run/docker.sock \
-v ${PWD}:/work \
-w /work \
indamutsa/working-image:1.0.0 zsh

docker run -it --rm --net=host \
--name working-container \
-e KUBECONFIG=/Users/aindamutsa/.kube/config \
-e MINIKUBE_HOME=/Users/aindamutsa/.minikube \
-v /var/run/docker.sock:/var/run/docker.sock \
-v ${HOME}/.kube/:/Users/aindamutsa/.kube/ \
-v ${HOME}/.minikube/:/Users/aindamutsa/.minikube/ \
-v ${PWD}:/work \
-w /work \
indamutsa/working-image:1.0.0 zsh


docker run -it --rm --net=host
--name working-container
-e KUBECONFIG=/Users/aindamutsa/.kube/config
-e MINIKUBE_HOME=/Users/aindamutsa/.minikube
-v /var/run/docker.sock:/var/run/docker.sock
-v ${HOME}/.kube/:/Users/aindamutsa/.kube/
-v ${HOME}/.minikube/:/Users/aindamutsa/.minikube/
-v ${HOME}/.minikube/profiles/minikube/client.crt:/Users/aindamutsa/.minikube/profiles/minikube/client.crt
-v ${HOME}/.minikube/profiles/minikube/client.key:/Users/aindamutsa/.minikube/profiles/minikube/client.key
-v ${HOME}/.minikube/ca.crt:/Users/aindamutsa/.minikube/ca.crt
-v ${PWD}:/work
-w /work
indamutsa/working-image:1.0.0 zsh


# docker tag basf-data-generator indamutsa/data-generator-item:1.0.2; 
# docker tag basf-angular indamutsa/angular-item:1.0.2;  
# docker tag basf-app indamutsa/app-item:1.0.2;

# docker push indamutsa/data-generator-item:1.0.2;
# docker push indamutsa/angular-item:1.0.2;
# docker push indamutsa/app-item:1.0.2;



docker tag basf-angular:latest indamutsa/angular-item:1.0.3;  
docker tag basf-app:latest indamutsa/app-item:1.0.3;

docker push indamutsa/angular-item:1.0.3;
docker push indamutsa/app-item:1.0.3;

docker rmi indamutsa/angular-item:1.0.3; 
docker rmi indamutsa/app-item:1.0.3;

docker rmi basf-angular:latest;
docker rmi basf-app:latest;
