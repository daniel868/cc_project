resource "kubernetes_deployment" "zookeeper_deployment" {
  metadata {
    name   = "zookeeper"
    labels = {
      app = "zookeeper"
    }
  }
  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "zookeeper"
      }
    }

    template {
      metadata {
        name   = "zookeeper"
        labels = {
          app = "zookeeper"
        }
      }

      spec {
        container {
          name              = "zookeeper"
          image             = "zookeeper:latest"
          image_pull_policy = "IfNotPresent"

          port {
            container_port = 2181
          }
        }
      }
    }
  }
}

resource "kubernetes_deployment" "kafka_deployment" {
  metadata {
    name   = "kafka"
    labels = {
      app = "kafka"
    }
  }
  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "kafka"
      }
    }

    template {
      metadata {
        name   = "kafka"
        labels = {
          app = "kafka"
        }
      }

      spec {
        container {
          name              = "kafka"
          image             = "bitnami/kafka"
          image_pull_policy = "IfNotPresent"

          port {
            container_port = 9092
          }
          env_from {
            config_map_ref {
              name = "kafka-config"
            }
          }
        }
      }
    }
  }
}

resource "kubernetes_service" "zookeeper_service" {
  metadata {
    name   = "zookeeper-service"
    labels = {
      app = "zookeeper"
    }
  }
  spec {
    selector = {
      app = "zookeeper"
    }
    port {
      port        = 2181
      target_port = 2181
    }
    type = "NodePort"
  }
}

resource "kubernetes_service" "kafka_service" {
  metadata {
    name = "kafka-service"
  }
  spec {
    selector = {
      app = "kafka"
    }
    port {
      port = 9092

    }
    type = "NodePort"
  }
}

resource "kubernetes_config_map" "kafka-config" {
  metadata {
    name = "kafka-config"
  }
  data = {
    KAFKA_BROKER_ID         = "1"
    KAFKA_ZOOKEEPER_CONNECT = "zookeeper-service:2181"
    KAFKA_LISTENERS         = "PLAINTEXT://:9092"
    KAFKA_ADVERTISED_LISTENERS : "PLAINTEXT://kafka-service:9092"
  }
}




