import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQuestion } from 'app/shared/model/question.model';

type EntityResponseType = HttpResponse<IQuestion>;
type EntityArrayResponseType = HttpResponse<IQuestion[]>;

@Injectable({ providedIn: 'root' })
export class QuestionService {
    public resourceUrl = SERVER_API_URL + 'api/questions';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/questions';

    constructor(private http: HttpClient) {}

    create(question: IQuestion): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(question);
        return this.http
            .post<IQuestion>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(question: IQuestion): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(question);
        return this.http
            .put<IQuestion>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IQuestion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IQuestion[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IQuestion[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(question: IQuestion): IQuestion {
        const copy: IQuestion = Object.assign({}, question, {
            createdDate: question.createdDate != null && question.createdDate.isValid() ? question.createdDate.toJSON() : null,
            updatedDate: question.updatedDate != null && question.updatedDate.isValid() ? question.updatedDate.toJSON() : null,
            sendForApprovalDate:
                question.sendForApprovalDate != null && question.sendForApprovalDate.isValid()
                    ? question.sendForApprovalDate.toJSON()
                    : null,
            approvedDate: question.approvedDate != null && question.approvedDate.isValid() ? question.approvedDate.toJSON() : null
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
            res.body.forEach((question: IQuestion) => {
                question.createdDate = question.createdDate != null ? moment(question.createdDate) : null;
                question.updatedDate = question.updatedDate != null ? moment(question.updatedDate) : null;
                question.sendForApprovalDate = question.sendForApprovalDate != null ? moment(question.sendForApprovalDate) : null;
                question.approvedDate = question.approvedDate != null ? moment(question.approvedDate) : null;
            });
        }
        return res;
    }
}
