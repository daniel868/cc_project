resource "kubernetes_config_map" "auth_config" {
  metadata {
    name = "auth-config"
  }
  data = {
    SPRING_PROFILES_ACTIVE = "k8s"
  }
}

resource "kubernetes_deployment" "auth" {
  metadata {
    name   = "auth"
    labels = {
      app = "auth"
    }
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "auth"
      }
    }

    template {
      metadata {
        name   = "auth"
        labels = {
          app = "auth"
        }
      }

      spec {
        container {
          name  = "auth"
          image = "silviadragan/ccauth:latest"

          port {
            container_port = 8080
          }

          env_from {
            config_map_ref {
              name = "auth-config"
            }
          }
        }
      }
    }
  }
}


resource "kubernetes_service" "auth_service" {
  metadata {
    name = "auth-service"
  }
  spec {
    selector = {
      app = "auth"
    }
    type = "NodePort"
    port {
      port        = 8081
      target_port = 8080
    }
  }
}
