/*
 * Copyright (C) 2015 Pawel Hajduk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.phajduk.rxfileobserver;

import android.os.FileObserver;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum FileEventType {
    ACCESS, MODIFY, ATTRIB, CLOSE_WRITE, CLOSE_NOWRITE, OPEN, MOVED_FROM, MOVED_TO, CREATE, DELETE, DELETE_SELF, MOVE_SELF;
    private static final HashMap<Integer, FileEventType> fileObserverMap = new HashMap<>();

    static {
        fileObserverMap.put(FileObserver.ACCESS, FileEventType.ACCESS);
        fileObserverMap.put(FileObserver.MODIFY, FileEventType.MODIFY);
        fileObserverMap.put(FileObserver.ATTRIB, FileEventType.ATTRIB);
        fileObserverMap.put(FileObserver.CLOSE_WRITE, FileEventType.CLOSE_WRITE);
        fileObserverMap.put(FileObserver.CLOSE_NOWRITE, FileEventType.CLOSE_NOWRITE);
        fileObserverMap.put(FileObserver.OPEN, FileEventType.OPEN);
        fileObserverMap.put(FileObserver.MOVED_FROM, FileEventType.MOVED_FROM);
        fileObserverMap.put(FileObserver.MOVED_TO, FileEventType.MOVED_TO);
        fileObserverMap.put(FileObserver.CREATE, FileEventType.CREATE);
        fileObserverMap.put(FileObserver.DELETE, FileEventType.DELETE);
        fileObserverMap.put(FileObserver.DELETE_SELF, FileEventType.DELETE_SELF);
        fileObserverMap.put(FileObserver.MOVE_SELF, FileEventType.MOVE_SELF);
    }

    public static EnumSet<FileEventType> create(int event) {
        EnumSet<FileEventType> result = EnumSet.noneOf(FileEventType.class);
        for(Map.Entry<Integer, FileEventType> entry : fileObserverMap.entrySet()) {
            if((entry.getKey() & event) == entry.getKey()) {
                result.add(entry.getValue());
            }
        }

        return result;
    }

}
