resource "kubernetes_deployment" "fe_deployment" {
  metadata {
    name   = "fe"
    labels = {
      app = "fe"
    }
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "fe"
      }
    }

    template {
      metadata {
        labels = {
          app = "fe"
        }
      }

      spec {
        container {
          image = "alexandrudaniel/cc_fe:latest"
          name  = "cc-fe"

          port {
            container_port = 4200
          }

        }
      }
    }
  }
}

resource "kubernetes_service" "fe_service" {
  metadata {
    name = "fe-service"
  }
  spec {
    selector = {
      app = "fe"
    }
    type = "LoadBalancer"
    port {
      port        = 4200
      target_port = 4200
    }
  }
}
