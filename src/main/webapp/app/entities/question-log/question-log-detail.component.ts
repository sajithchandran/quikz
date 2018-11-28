import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestionLog } from 'app/shared/model/question-log.model';

@Component({
    selector: 'jhi-question-log-detail',
    templateUrl: './question-log-detail.component.html'
})
export class QuestionLogDetailComponent implements OnInit {
    questionLog: IQuestionLog;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ questionLog }) => {
            this.questionLog = questionLog;
        });
    }

    previousState() {
        window.history.back();
    }
}
