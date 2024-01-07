terraform {
  required_providers {
    kind = {
      source = "tehcyx/kind"
      version = "0.2.1"
    }
  }
}

provider "kind" {}

resource "kind_cluster" "default" {
  name = "cc-cluster"
  node_image = "kindest/node:v1.27.3"

  kind_config {
    api_version = "kind.x-k8s.io/v1alpha4"
    kind        = "Cluster"
    node {
      role = "control-plane"
    }
    node {
      role = "worker"
    }
  }
}



