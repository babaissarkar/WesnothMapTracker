# Map Tracker
A JavaFX based GUI Map Tracking tool for Battle for Wesnoth.

[![License: GPL v2](https://img.shields.io/badge/License-GPL%20v2-blue.svg)](https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html) [![CI](https://github.com/babaissarkar/WesnothMapTracker/actions/workflows/maven-publish.yml/badge.svg)](https://github.com/babaissarkar/WesnothMapTracker/actions/workflows/maven-publish.yml?query=branch%3Amain)

# Installation
Head over to the **Actions** tab, then download the artifacts for your OS from the latest CI run, such as this (https://github.com/babaissarkar/WesnothMapTracker/actions/runs/12048185526).

# Usage
* Click **Open Background** from the **File** menu to select a background to draw the markers on.
* Some markers are preloaded. Click **Load more graphics** button and select Battle for Wesnoth *misc* directory (the one with the new_journey/new_rest marker etc.) or your directory with more images to use them as markers.
* Select a marker from the adjoining list and click on the background to draw. For preloaded markers, their corresponding macros which will be generated is defined [here](https://github.com/babaissarkar/WesnothMapTracker/blob/main/src/main/resources/names.properties). (You can map any other markers by editing the properties file and rebuilding, or can request me to add it.)
* Save using **File > Save** once you are done (or copy/paste from the output text area in the bottom pane). The output is like this:
```
  {NEW_JOURNEY 455 300}
  {NEW_REST 500 300}
```

# How to build
You'll need Java 21 and Maven. To build, clone this repository, then run `mvn package --file pom-for-your-distro.xml` or `mvn package --file pom-standalone.xml` for building the jar only (the final jar can be found in `jar` subfolder, while the packages are generated in the same folder as the pom files).

# Screenshots
![Main Window](/screenshots/maptracker1.png "Map Tracker (initial)")
![Main Window in Action](/screenshots/maptracker2.png "Map Tracker in Action")

# License
Available under the [GNU GPL 2.0 license](https://www.gnu.org/licenses/old-licenses/gpl-2.0.html).
