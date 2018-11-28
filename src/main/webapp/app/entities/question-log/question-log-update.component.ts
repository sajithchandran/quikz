import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IQuestionLog } from 'app/shared/model/question-log.model';
import { QuestionLogService } from './question-log.service';
import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from 'app/entities/question';

@Component({
    selector: 'jhi-question-log-update',
    templateUrl: './question-log-update.component.html'
})
export class QuestionLogUpdateComponent implements OnInit {
    questionLog: IQuestionLog;
    isSaving: boolean;

    questions: IQuestion[];
    createdDate: string;
    updatedDate: string;
    sendForApprovalDate: string;
    approvedDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private questionLogService: QuestionLogService,
        private questionService: QuestionService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ questionLog }) => {
            this.questionLog = questionLog;
            this.createdDate = this.questionLog.createdDate != null ? this.questionLog.createdDate.format(DATE_TIME_FORMAT) : null;
            this.updatedDate = this.questionLog.updatedDate != null ? this.questionLog.updatedDate.format(DATE_TIME_FORMAT) : null;
            this.sendForApprovalDate =
                this.questionLog.sendForApprovalDate != null ? this.questionLog.sendForApprovalDate.format(DATE_TIME_FORMAT) : null;
            this.approvedDate = this.questionLog.approvedDate != null ? this.questionLog.approvedDate.format(DATE_TIME_FORMAT) : null;
        });
        this.questionService.query().subscribe(
            (res: HttpResponse<IQuestion[]>) => {
                this.questions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.questionLog.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        this.questionLog.updatedDate = this.updatedDate != null ? moment(this.updatedDate, DATE_TIME_FORMAT) : null;
        this.questionLog.sendForApprovalDate = this.sendForApprovalDate != null ? moment(this.sendForApprovalDate, DATE_TIME_FORMAT) : null;
        this.questionLog.approvedDate = this.approvedDate != null ? moment(this.approvedDate, DATE_TIME_FORMAT) : null;
        if (this.questionLog.id !== undefined) {
            this.subscribeToSaveResponse(this.questionLogService.update(this.questionLog));
        } else {
            this.subscribeToSaveResponse(this.questionLogService.create(this.questionLog));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionLog>>) {
        result.subscribe((res: HttpResponse<IQuestionLog>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackQuestionById(index: number, item: IQuestion) {
        return item.id;
    }
}
