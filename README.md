# RxFileObserver
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-RxFileObserver-green.svg?style=flat)](https://android-arsenal.com/details/1/2397)

Reactive wrapper around Android's [FileObserver][1]

## Download
[![Maven Central](https://img.shields.io/maven-central/v/com.github.phajduk/rxfileobserver.svg)](http://search.maven.org/#search%7Cga%7C1%7Crxfileobserver)

Gradle:
```groovy
compile 'com.github.phajduk:rxfileobserver:0.1.0'
```

## Example
Subscribe to any file system changes performed in external storage directory:

```java
File sdCard = Environment.getExternalStorageDirectory();
Observable<FileEvent> sdCardFileEvents = RxFileObserver.create(sdCard);
sdCardFileEvents.subscribe(new Action1<FileEvent>() {
    @Override
    public void call(FileEvent fileEvent) {
        Log.i(TAG, fileEvent.toString());
    }
});
````

## Documentation
Emitted `FileEvent` contains:
```java
EnumSet<FileEventType> eventTypeEnumSet;
String path;
```
- `path` it's just path of changed/removed/created file
- `eventTypeEnumSet` gives you information about flags

### Flags
```java
public enum FileEventType {
    ACCESS, // Data was read from a file
    MODIFY, // Data was written to a file
    ATTRIB, // Metadata (permissions, owner, timestamp) was changed explicitly
    CLOSE_WRITE, // Someone had a file or directory open for writing, and closed it
    CLOSE_NOWRITE, // Someone had a file or directory open read-only, and closed it
    OPEN, // A file or directory was opened
    MOVED_FROM, // A file or subdirectory was moved from the monitored directory
    MOVED_TO, // A file or subdirectory was moved to the monitored directory
    CREATE, // A new file or subdirectory was created under the monitored directory
    DELETE, // A file was deleted from the monitored directory
    DELETE_SELF, // The monitored file or directory was deleted; monitoring effectively stops
    MOVE_SELF; // The monitored file or directory was moved; monitoring continues
    (...)
}
```
# License


    Copyright 2015 Pawel Hajduk

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


 [1]: http://developer.android.com/reference/android/os/FileObserver.html
