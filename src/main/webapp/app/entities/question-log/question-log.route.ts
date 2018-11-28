import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { QuestionLog } from 'app/shared/model/question-log.model';
import { QuestionLogService } from './question-log.service';
import { QuestionLogComponent } from './question-log.component';
import { QuestionLogDetailComponent } from './question-log-detail.component';
import { QuestionLogUpdateComponent } from './question-log-update.component';
import { QuestionLogDeletePopupComponent } from './question-log-delete-dialog.component';
import { IQuestionLog } from 'app/shared/model/question-log.model';

@Injectable({ providedIn: 'root' })
export class QuestionLogResolve implements Resolve<IQuestionLog> {
    constructor(private service: QuestionLogService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<QuestionLog> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<QuestionLog>) => response.ok),
                map((questionLog: HttpResponse<QuestionLog>) => questionLog.body)
            );
        }
        return of(new QuestionLog());
    }
}

export const questionLogRoute: Routes = [
    {
        path: 'question-log',
        component: QuestionLogComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionLogs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'question-log/:id/view',
        component: QuestionLogDetailComponent,
        resolve: {
            questionLog: QuestionLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionLogs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'question-log/new',
        component: QuestionLogUpdateComponent,
        resolve: {
            questionLog: QuestionLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionLogs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'question-log/:id/edit',
        component: QuestionLogUpdateComponent,
        resolve: {
            questionLog: QuestionLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionLogs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const questionLogPopupRoute: Routes = [
    {
        path: 'question-log/:id/delete',
        component: QuestionLogDeletePopupComponent,
        resolve: {
            questionLog: QuestionLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionLogs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
