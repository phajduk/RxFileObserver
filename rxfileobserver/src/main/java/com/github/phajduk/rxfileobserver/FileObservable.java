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

package com.github.phajduk.rxfileobserver;

import android.os.FileObserver;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

public class FileObservable implements Observable.OnSubscribe<FileEvent> {

    private String pathToWatch;

    public FileObservable(String pathToWatch) {
        this.pathToWatch = pathToWatch;
    }

    @Override
    public void call(final Subscriber<? super FileEvent> subscriber) {
        final FileObserver observer = new FileObserver(pathToWatch) {
            @Override
            public void onEvent(int event, String file) {
                if(subscriber.isUnsubscribed()) {
                    return;
                }

                FileEvent fileEvent = FileEvent.create(event, file);
                subscriber.onNext(fileEvent);

                if(fileEvent.isDeleteSelf()) {
                    subscriber.onCompleted();
                }
            }
        };
        observer.startWatching(); //START OBSERVING

        subscriber.add(Subscriptions.create(new Action0() {
            @Override
            public void call() {
                observer.stopWatching();
            }
        }));
    }
}
