import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from './question.service';
import { IUser, UserService } from 'app/core';
import { ICountry } from 'app/shared/model/country.model';
import { CountryService } from 'app/entities/country';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';
import { ITopic } from 'app/shared/model/topic.model';
import { TopicService } from 'app/entities/topic';

@Component({
    selector: 'jhi-question-update',
    templateUrl: './question-update.component.html'
})
export class QuestionUpdateComponent implements OnInit {
    question: IQuestion;
    isSaving: boolean;

    users: IUser[];

    countries: ICountry[];

    categories: ICategory[];

    topics: ITopic[];
    createdDate: string;
    updatedDate: string;
    sendForApprovalDate: string;
    approvedDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private questionService: QuestionService,
        private userService: UserService,
        private countryService: CountryService,
        private categoryService: CategoryService,
        private topicService: TopicService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ question }) => {
            this.question = question;
            this.createdDate = this.question.createdDate != null ? this.question.createdDate.format(DATE_TIME_FORMAT) : null;
            this.updatedDate = this.question.updatedDate != null ? this.question.updatedDate.format(DATE_TIME_FORMAT) : null;
            this.sendForApprovalDate =
                this.question.sendForApprovalDate != null ? this.question.sendForApprovalDate.format(DATE_TIME_FORMAT) : null;
            this.approvedDate = this.question.approvedDate != null ? this.question.approvedDate.format(DATE_TIME_FORMAT) : null;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.countryService.query({ filter: 'question-is-null' }).subscribe(
            (res: HttpResponse<ICountry[]>) => {
                if (!this.question.country || !this.question.country.id) {
                    this.countries = res.body;
                } else {
                    this.countryService.find(this.question.country.id).subscribe(
                        (subRes: HttpResponse<ICountry>) => {
                            this.countries = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.categoryService.query({ filter: 'question-is-null' }).subscribe(
            (res: HttpResponse<ICategory[]>) => {
                if (!this.question.category || !this.question.category.id) {
                    this.categories = res.body;
                } else {
                    this.categoryService.find(this.question.category.id).subscribe(
                        (subRes: HttpResponse<ICategory>) => {
                            this.categories = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.topicService.query({ filter: 'question-is-null' }).subscribe(
            (res: HttpResponse<ITopic[]>) => {
                if (!this.question.topic || !this.question.topic.id) {
                    this.topics = res.body;
                } else {
                    this.topicService.find(this.question.topic.id).subscribe(
                        (subRes: HttpResponse<ITopic>) => {
                            this.topics = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.question.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        this.question.updatedDate = this.updatedDate != null ? moment(this.updatedDate, DATE_TIME_FORMAT) : null;
        this.question.sendForApprovalDate = this.sendForApprovalDate != null ? moment(this.sendForApprovalDate, DATE_TIME_FORMAT) : null;
        this.question.approvedDate = this.approvedDate != null ? moment(this.approvedDate, DATE_TIME_FORMAT) : null;
        if (this.question.id !== undefined) {
            this.subscribeToSaveResponse(this.questionService.update(this.question));
        } else {
            this.subscribeToSaveResponse(this.questionService.create(this.question));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IQuestion>>) {
        result.subscribe((res: HttpResponse<IQuestion>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackCountryById(index: number, item: ICountry) {
        return item.id;
    }

    trackCategoryById(index: number, item: ICategory) {
        return item.id;
    }

    trackTopicById(index: number, item: ITopic) {
        return item.id;
    }
}
