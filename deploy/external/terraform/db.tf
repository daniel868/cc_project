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
    type = "LoadBalancer"
    port {
      port        = 5050
      target_port = 8080
    }
  }
}


resource "kubernetes_config_map" "db-config-auth" {
  metadata {
    name = "db-config-auth"
  }
  data = {
    POSTGRES_USER     = "admin"
    POSTGRES_PASSWORD = "admin"
    POSTGRES_DB       = "authdb"
  }
}

resource "kubernetes_deployment" "postgresql-auth" {
  metadata {
    name   = "postgresql-auth"
    labels = {
      app = "postgresql-auth"
    }
  }
  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "postgresql-auth"
      }
    }

    template {
      metadata {
        name   = "postgresql-auth"
        labels = {
          app = "postgresql-auth"
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

resource "kubernetes_service" "db-auth-service" {
  metadata {
    name   = "db-auth-service"
    labels = {
      app = "postgresql-auth"
    }
  }
  spec {
    selector = {
      app = "postgresql-auth"
    }

    type = "ClusterIP"
    port {
      port = 5432
    }
  }
}
