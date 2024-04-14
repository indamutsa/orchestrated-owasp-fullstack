Start the backend server by running the following command:

```bash
cd backend
./start-backend
```

populate the database with the following command:

```bash
cd ../data-gen
python3 -m venv pyenv
source pyenv/bin/activate
python main.py
```

docker run -it --rm --net host --name working-container \
-e KUBECONFIG=/Users/aindamutsa/.kube/config \
-e MINIKUBE_HOME=/Users/aindamutsa/.minikube \
-v /var/run/docker.sock:/var/run/docker.sock \
-v ${HOME}/.kube/:/Users/aindamutsa/.kube/ \
-v ${HOME}/.minikube/:/Users/aindamutsa/.minikube/ \
-v ${PWD}:/work \
-w /work indamutsa/working-image:1.0.0 zsh
