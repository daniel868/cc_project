resource "kubernetes_config_map" "db-config" {
  metadata {
    name = "db-config"
  }
  data = {
    POSTGRES_USER     = "admin"
    POSTGRES_PASSWORD = "admin"
    POSTGRES_DB       = "test_db"
  }
}

resource "kubernetes_deployment" "postgresql" {
  metadata {
    name   = "postgresql"
    labels = {
      app = "postgresql"
    }
  }
  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "postgresql"
      }
    }

    template {
      metadata {
        name   = "postgresql"
        labels = {
          app = "postgresql"
        }
      }

      spec {
        container {
          name              = "postgresql"
          image             = "postgres:10.4"
          image_pull_policy = "IfNotPresent"

          port {
            container_port = 5432
          }

          env_from {
            config_map_ref {
              name = "db-config"
            }
          }

        }
      }
    }
  }
}

resource "kubernetes_service" "db-service" {
  metadata {
    name   = "db-service"
    labels = {
      app = "postgresql"
    }
  }
  spec {
    selector = {
      app = "postgresql"
    }

    type = "ClusterIP"
    port {
      port = 5432
    }
  }
}


resource "kubernetes_config_map" "db-adminer-configmap" {
  metadata {
    name = "db-adminer-configmap"
  }
  data = {
    ADMINER_DESIGN         = "pepa-linha"
    ADMINER_DEFAULT_SERVER = "postgres"
  }
}

resource "kubernetes_deployment" "db-admin" {
  metadata {
    name   = "db-admin"
    labels = {
      app = "db-admin"
    }
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "db-admin"
      }
    }

    template {
      metadata {
        labels = {
          app = "db-admin"
        }
      }

      spec {
        container {
          image = "adminer:4.7.6-standalone"
          name  = "db-adminer"

          port {
            container_port = 8080
          }

          env_from {
            config_map_ref {
              name = "db-adminer-configmap"
            }
          }
        }
      }
    }
  }
}

resource "kubernetes_service" "adminer_service" {
  metadata {
    name = "adminer-service"
  }
  spec {
    selector = {
      app = "db-admin"
    }
    type = "NodePort"
    port {
      port        = 5050
      target_port = 8080
      node_port   = 32000
    }
  }
}

#springboot deployment example

#resource "kubernetes_config_map" "api_config" {
#  metadata {
#    name = "api-config"
#  }
#  data = {
#    SPRING_PROFILES_ACTIVE = "k8s"
#  }
#}
#
#resource "kubernetes_deployment" "api_pod" {
#  metadata {
#    name   = "api-pod"
#    labels = {
#      app = "api-pod"
#    }
#  }
#
#  spec {
#    replicas = 2
#
#    selector {
#      match_labels = {
#        app = "api-pod"
#      }
#    }
#
#    template {
#      metadata {
#        name   = "api-pod"
#        labels = {
#          app = "api-pod"
#        }
#      }
#
#      spec {
#        container {
#          name  = "api-pod"
#          image = "alexandrudaniel/my-spring-app:latest"
#
#          port {
#            container_port = 8080
#          }
#
#          env_from {
#            config_map_ref {
#              name = "api-config"
#            }
#          }
#        }
#        dns_config {
#          option {
#            name = "Default"
#          }
#        }
#      }
#    }
#  }
#}
#
#resource "kubernetes_service" "api_service" {
#  metadata {
#    name = "api-service"
#  }
#  spec {
#    selector = {
#      app = "api-pod"
#    }
#    type = "NodePort"
#    port {
#      port        = 80
#      target_port = 8080
#    }
#  }
#}
