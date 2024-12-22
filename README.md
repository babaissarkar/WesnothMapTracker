# Map Tracker
A JavaFX based GUI Map Tracking tool for Battle for Wesnoth.

[![License: GPL v2](https://img.shields.io/badge/License-GPL%20v2-blue.svg)](https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html)

# Installation
Head over to the **Actions** tab, then download the artifacts for your OS from the latest CI run, such as this (https://github.com/babaissarkar/WesnothMapTracker/actions/runs/12048185526).

# Usage
* Click **Open Background** from the **File** menu to select a background to draw the markers on.
* Click **Add more markers** button and select Battle for Wesnoth *misc* directory (the one with the new_journey/new_rest marker etc.).
* Select a marker from the adjoining list and click on the background to draw. Supported markers and their corresponding macros which will be generated can be [here](https://github.com/babaissarkar/WesnothMapTracker/blob/main/src/main/resources/names.properties). (You can map any other markers by editing the properties file and rebuilding, or can request me to add it.)
* Save using **File > Save** once you are done. The output is like this:
```
  {NEW_JOURNEY 455 300}
  {NEW_REST 500 300}
```

# How to build
You'll need Java 21 and Maven. To build, run `mvn package --file pom-for-your-distro.xml` or `mvn package --file pom-standalone.xml` for building the jar only (the final jar can be found in `jar` subfolder, while the packages are generated in the same folder as the pom files).

# Screenshots
![Main Window in Action](/screenshots/maptracker.png "Map Tracker in Action")

# License
Available under the [GNU GPL 2.0 license](https://www.gnu.org/licenses/old-licenses/gpl-2.0.html).
