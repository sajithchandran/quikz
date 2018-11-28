/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QuikzTestModule } from '../../../test.module';
import { QuestionLogDetailComponent } from 'app/entities/question-log/question-log-detail.component';
import { QuestionLog } from 'app/shared/model/question-log.model';

describe('Component Tests', () => {
    describe('QuestionLog Management Detail Component', () => {
        let comp: QuestionLogDetailComponent;
        let fixture: ComponentFixture<QuestionLogDetailComponent>;
        const route = ({ data: of({ questionLog: new QuestionLog(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QuikzTestModule],
                declarations: [QuestionLogDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(QuestionLogDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuestionLogDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.questionLog).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
