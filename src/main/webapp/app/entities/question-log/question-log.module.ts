import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QuikzSharedModule } from 'app/shared';
import {
    QuestionLogComponent,
    QuestionLogDetailComponent,
    QuestionLogUpdateComponent,
    QuestionLogDeletePopupComponent,
    QuestionLogDeleteDialogComponent,
    questionLogRoute,
    questionLogPopupRoute
} from './';

const ENTITY_STATES = [...questionLogRoute, ...questionLogPopupRoute];

@NgModule({
    imports: [QuikzSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        QuestionLogComponent,
        QuestionLogDetailComponent,
        QuestionLogUpdateComponent,
        QuestionLogDeleteDialogComponent,
        QuestionLogDeletePopupComponent
    ],
    entryComponents: [QuestionLogComponent, QuestionLogUpdateComponent, QuestionLogDeleteDialogComponent, QuestionLogDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QuikzQuestionLogModule {}
