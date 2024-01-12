terraform {
  required_version = ">= 0.13"
  required_providers {
    minikube = {
      source  = "scott-the-programmer/minikube"
      version = "0.3.7"
    }
    kubectl = {
      source  = "gavinbunney/kubectl"
      version = ">= 1.7.0"
    }
  }
}

provider "kubernetes" {
  config_path    = "~/.kube/config"
  config_context = "multinode-cluster"
}
