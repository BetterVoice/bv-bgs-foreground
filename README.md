# Foreground Service Plugin (bv-bgs-foreground)

## Quick summary
This repository provides a background service which launches in the Foreground with a notification. It was built on the core functionality for the Background Service Plugin for Cordova (bgs-core). While this Foreground service is active, it will PREVENT the main Cordova WebView activity from getting killed by the Android OS.

The main bgs-core plugin is provided in the https://github.com/Red-Folder/bgs-core repository.

## Getting started
All methods of the bgs-core plugin are accessible.

The only difference is that this plugin builds a notification using the apps default icon and launches the service in the Foreground (`startForeground`) using the notification. You can pass a `notificationTitle` and `notificationText` parameter to the `setConfiguration` function in order to update the Foreground Notification's Title and Text.

## Licence
Copyright 2013 Red Folder Consultancy Ltd
    
Licensed under the Apache License, Version 2.0 (the "License");   
you may not use this file except in compliance with the License.   
You may obtain a copy of the License at       
  
http://www.apache.org/licenses/LICENSE-2.0   
 
Unless required by applicable law or agreed to in writing, software   
distributed under the License is distributed on an "AS IS" BASIS,   
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   
See the License for the specific language governing permissions and   
limitations under the License.