resource "kubernetes_config_map" "business_logic_config" {
  metadata {
    name = "business-logic-config"
  }
  data = {
    SPRING_PROFILES_ACTIVE = "k8s"
  }
}

resource "kubernetes_deployment" "business_logic" {
  metadata {
    name   = "business-logic"
    labels = {
      app = "business-logic"
    }
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "business-logic"
      }
    }

    template {
      metadata {
        name   = "business-logic"
        labels = {
          app = "business-logic"
        }
      }

      spec {
        container {
          name  = "business-logic"
          image = "cristianaandrei/cc:business_logic"

          port {
            container_port = 8080
          }

          env_from {
            config_map_ref {
              name = "business-logic-config"
            }
          }
        }
      }
    }
  }
}


resource "kubernetes_service" "business_logic_service" {
  metadata {
    name = "business-logic-service"
  }
  spec {
    selector = {
      app = "business-logic"
    }
    type = "NodePort"
    port {
      port        = 8082
      target_port = 8080
    }
  }
}
