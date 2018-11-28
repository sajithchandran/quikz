import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQuestionLog } from 'app/shared/model/question-log.model';

type EntityResponseType = HttpResponse<IQuestionLog>;
type EntityArrayResponseType = HttpResponse<IQuestionLog[]>;

@Injectable({ providedIn: 'root' })
export class QuestionLogService {
    public resourceUrl = SERVER_API_URL + 'api/question-logs';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/question-logs';

    constructor(private http: HttpClient) {}

    create(questionLog: IQuestionLog): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(questionLog);
        return this.http
            .post<IQuestionLog>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(questionLog: IQuestionLog): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(questionLog);
        return this.http
            .put<IQuestionLog>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IQuestionLog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IQuestionLog[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IQuestionLog[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(questionLog: IQuestionLog): IQuestionLog {
        const copy: IQuestionLog = Object.assign({}, questionLog, {
            createdDate: questionLog.createdDate != null && questionLog.createdDate.isValid() ? questionLog.createdDate.toJSON() : null,
            updatedDate: questionLog.updatedDate != null && questionLog.updatedDate.isValid() ? questionLog.updatedDate.toJSON() : null,
            sendForApprovalDate:
                questionLog.sendForApprovalDate != null && questionLog.sendForApprovalDate.isValid()
                    ? questionLog.sendForApprovalDate.toJSON()
                    : null,
            approvedDate: questionLog.approvedDate != null && questionLog.approvedDate.isValid() ? questionLog.approvedDate.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
            res.body.updatedDate = res.body.updatedDate != null ? moment(res.body.updatedDate) : null;
            res.body.sendForApprovalDate = res.body.sendForApprovalDate != null ? moment(res.body.sendForApprovalDate) : null;
            res.body.approvedDate = res.body.approvedDate != null ? moment(res.body.approvedDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((questionLog: IQuestionLog) => {
                questionLog.createdDate = questionLog.createdDate != null ? moment(questionLog.createdDate) : null;
                questionLog.updatedDate = questionLog.updatedDate != null ? moment(questionLog.updatedDate) : null;
                questionLog.sendForApprovalDate = questionLog.sendForApprovalDate != null ? moment(questionLog.sendForApprovalDate) : null;
                questionLog.approvedDate = questionLog.approvedDate != null ? moment(questionLog.approvedDate) : null;
            });
        }
        return res;
    }
}
