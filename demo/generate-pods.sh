#!/bin/bash

NODES=$(kubectl get nodes --no-headers -o custom-columns=NODE:.metadata.name)

N=0

for NODE in $NODES ; do
	cat pod.yaml | sed "s/\$(N)/$N/" | sed "s/\$(NODE)/$NODE/" > p${N}.yaml
	N=$(($N+1))
done