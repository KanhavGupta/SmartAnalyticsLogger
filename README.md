# SmartAnalyticsLogger

A lightweight, thread-safe analytics SDK for Android (min SDK 24) designed to reliably log custom events both online and offline, without relying on heavy third-party analytics tools.

## High-Level Design Overview

The SDK is built with a modular and minimal architecture using only standard Android/Kotlin libraries:

### Architecture Components:
- **AnalyticsLogger (public singleton)**: SDK entry point to initialize and log events.
- **AnalyticsCore**: Internal class that wires up event queuing and uploading.
- **EventQueueManager**: Handles local JSON persistence with file-based storage.
- **EventUploader**: Uploads events via HTTP using OkHttp; skips upload if offline.
- **Event**: Simple data class holding event name, properties, and timestamp.

### Event Flow:
AnalyticsLogger.logEvent(...) →
    AnalyticsCore →
        EventQueueManager.enqueue(event) →
        EventUploader.tryUploadPendingEvents() →
            POST to given uploadUrl if online

            
## Build & Run Instructions
### As a Library
  Clone this repo:   git clone https://github.com/KanhavGupta/SmartAnalyticsLogger.git
  Open in Android Studio.

  Build the smartlogger module to generate an .aar file:

  Go to Build > Make Module 'smartlogger'.

### Use in another Android app:
  Place the generated .aar (e.g., smartlogger-release.aar) inside your app’s /libs directory.

  Add this to build.gradle:
  
  repositories {
      flatDir {
          dirs 'libs'
      }
  }
  
  dependencies {
      implementation(name: 'smartlogger-release', ext: 'aar')
  }
  
## Trade-offs & Assumptions
  I used File-based storage as it is lightweight and easy for JSON serialization; no Room or DB overhead
  OkHttp was used for network since it is reliable and has small footprint; no Retrofit used for simplicity
  Used Manual locking(ReentrantLock), it ensures thread safety across background & UI threads
  There is no UI/visual dashboard SDK responsibility ends at transmission; visualization left to user server
  Upload URL is user-defined, it allows usage with Webhook.site, custom endpoints, or future backend integration

### Requirements
  Min SDK: 24
  AGP: 8.5.2+
  Kotlin: 1.9+
